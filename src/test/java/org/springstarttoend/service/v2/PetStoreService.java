package org.springstarttoend.service.v2;

import org.springstarttoend.dao.v2.AccountDao;
import org.springstarttoend.dao.v2.ItemDao;

/**
 * @author mh_liu
 * @create 2018/8/10 下午5:20
 */
public class PetStoreService {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private String str;

    private int index;

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

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
