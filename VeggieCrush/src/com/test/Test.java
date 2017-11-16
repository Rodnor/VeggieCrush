package com.test;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.dao.AccountDao;
import com.entitie.Account;

public class Test {
	final static Logger logger = Logger.getLogger(Test.class.getName());


	public static void main(String[] args) {
		
		logger.info("Appel TEST_DEVV getUser");
		AccountDao accountDao = new AccountDao();
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		logger.debug("MiPa avant appel DAO");
		accounts = accountDao.getAllAccounts();	
		
		for (Account account : accounts) {
			logger.debug(account.toString());
		}

	}

}
