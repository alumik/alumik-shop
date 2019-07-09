package cn.alumik.shop.service;

import cn.alumik.shop.dao.HistoryRepository;
import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.History;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService {

    private UserRepository userRepository;

    private SecurityService securityService;

    private ItemRepository itemRepository;

    private HistoryRepository historyRepository;

    public HistoryServiceImpl(UserRepository userRepository, SecurityService securityService, ItemRepository itemRepository, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.itemRepository = itemRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public void saveHistory(Integer id) {
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        Optional<Item> itemOptional = itemRepository.findById(id);
        History history;
        if (itemOptional.isPresent()) {
            Optional<History> historyOptional = historyRepository.findByUserAndItem(user, itemOptional.get());
            if (historyOptional.isPresent()) {
                history = historyOptional.get();
            } else {
                history = new History();
                history.setUser(user);
                history.setItem(itemOptional.get());
            }
            history.setLastViewed(new Timestamp(System.currentTimeMillis()));
            historyRepository.save(history);
        }
    }
}
