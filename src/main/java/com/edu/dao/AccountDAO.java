package com.edu.dao;


import com.edu.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class AccountDAO{
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Account account) {
		getSession().save(account);
	}
	public Account findByUserId(String userId) {
		return (Account) getSession().get(Account.class,userId);
	}

	public Account getByUserId(String userId) {
		return (Account) getSession().get(Account.class,userId);
	}

	public void update(Account account) {
		this.getSession().update(account);
	}
	public Account getByCardNumber(String cardNumber) {
		String hql = "from Account where cardNumber = ?";
		Query query = getSession().createQuery(hql);
		query.setString(0,cardNumber);
		return (Account) query.uniqueResult();
	}


}