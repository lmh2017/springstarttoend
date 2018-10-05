package org.springstarttoend.service.v5;

import org.springstarttoend.beans.factory.annotation.Autowired;
import org.springstarttoend.dao.v4.AccountDao;
import org.springstarttoend.dao.v4.ItemDao;
import org.springstarttoend.stereotype.Component;
import org.springstarttoend.util.MessageTracker;

/**
 * @author mh_liu
 * @create 2018/9/10 下午7:17
 */
@Component(value = "petService")
public class PetService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void placeOrder(){
        System.out.println("placeOrder");
        MessageTracker.addMsg("placeOrder");
    }
}
