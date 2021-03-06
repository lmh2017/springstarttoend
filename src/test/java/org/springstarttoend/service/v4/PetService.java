package org.springstarttoend.service.v4;

import org.springstarttoend.beans.factory.annotation.Autowired;
import org.springstarttoend.dao.v4.AccountDao;
import org.springstarttoend.dao.v4.ItemDao;
import org.springstarttoend.stereotype.Component;

/**
 * @author mh_liu
 * @create 2018/9/10 下午7:17
 */
@Component(value = "petService")
public class PetService {

//    @Autowired
    private AccountDao accountDao;

//    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
