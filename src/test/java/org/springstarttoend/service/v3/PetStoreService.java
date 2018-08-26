package org.springstarttoend.service.v3;

import org.springstarttoend.dao.v3.AccountDao;
import org.springstarttoend.dao.v3.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private int version;

    public PetStoreService(AccountDao accountDao,ItemDao itemDao,int version){
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public int getVersion() {
        return version;
    }
}
