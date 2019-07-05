package cn.alumik.shop.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "favorite_user",
            joinColumns = @JoinColumn(name = "by_user"),
            inverseJoinColumns = @JoinColumn(name = "favorite_user")
    )
    private Set<User> favoriteUsers;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Cart> carts;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "favorite_item",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_item")
    )
    private Set<Item> favoriteItems;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Item> sellingItems;

    @Transient
    private String passwordConfirm;

    @Transient
    private Boolean isSuperAdmin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (roles != null) {
            roles.remove(role);
        }
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public void setFavoriteUsers(Set<User> favoriteUsers) {
        this.favoriteUsers = favoriteUsers;
    }

    public void addFavoriteUser(User user) {
        if (favoriteUsers == null) {
            favoriteUsers = new HashSet<>();
        }
        favoriteUsers.add(user);
    }

    public void removeFavoriteUser(User user) {
        if (favoriteUsers != null) {
            favoriteUsers.remove(user);
        }
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public void addCart(Cart cart) {
        if (carts == null) {
            carts = new HashSet<>();
        }
        carts.add(cart);
    }

    public void removeCart(Cart cart) {
        if (carts != null) {
            carts.remove(cart);
        }
    }

    public Set<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(Set<Item> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public void addFavoriteItem(Item item) {
        if (favoriteItems == null) {
            favoriteItems = new HashSet<>();
        }
        favoriteItems.add(item);
    }

    public void removeFavoriteItem(Item item) {
        if (favoriteItems != null) {
            favoriteItems.remove(item);
        }
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new HashSet<>();
        }
        transactions.add(transaction);
    }

    public Boolean getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        if (addresses == null) {
            addresses = new HashSet<>();
        }
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address) {
        if (addresses != null) {
            addresses.remove(address);
        }
    }

    public Set<Item> getSellingItems() {
        return sellingItems;
    }

    public void setSellingItems(Set<Item> sellingItems) {
        this.sellingItems = sellingItems;
    }
}
