package cn.alumik.shop.service;

import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;
    private SecurityService securityService;
    private UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, SecurityService securityService, UserService userService) {
        this.itemRepository = itemRepository;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    public void save(Item item) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        item.setSeller(user);
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        item.setAvailable(true);
        itemRepository.save(item);
    }
}
