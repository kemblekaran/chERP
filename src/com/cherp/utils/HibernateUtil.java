package com.cherp.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.cherp.entities.AdminLoginInfo;
import com.cherp.entities.Area;
import com.cherp.entities.Bank;
import com.cherp.entities.City;
import com.cherp.entities.Cleaners;
import com.cherp.entities.Company;
import com.cherp.entities.Customer;
import com.cherp.entities.DebitCredit;
import com.cherp.entities.Drivers;
import com.cherp.entities.Expenses;
import com.cherp.entities.Location;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Product;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.entities.SalesLoad;
import com.cherp.entities.State;
import com.cherp.entities.User;
import com.cherp.entities.Van;

public class HibernateUtil {
	private static HibernateUtil instance = null;

	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry serviceRegistry;

	private HibernateUtil() {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		config.addAnnotatedClass(Area.class);
		config.addAnnotatedClass(Purchase.class);
		config.addAnnotatedClass(PayLoad.class);
		config.addAnnotatedClass(Van.class);
		config.addAnnotatedClass(Drivers.class);
		config.addAnnotatedClass(Cleaners.class);
		config.addAnnotatedClass(Location.class);
		config.addAnnotatedClass(Product.class);
		config.addAnnotatedClass(Company.class);
		config.addAnnotatedClass(Sales.class);
		config.addAnnotatedClass(SalesLoad.class);
		config.addAnnotatedClass(State.class);
		config.addAnnotatedClass(Expenses.class);
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(DebitCredit.class);
		config.addAnnotatedClass(City.class);
		config.addAnnotatedClass(Bank.class);
		config.addAnnotatedClass(Cleaners.class);
		config.addAnnotatedClass(Customer.class);
		config.addAnnotatedClass(State.class);
		config.addAnnotatedClass(AdminLoginInfo.class);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
