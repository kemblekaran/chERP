package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Area;
import com.cherp.entities.Location;
import com.cherp.utils.HibernateUtil;

public class LocationDao extends MasterBaseDao {
	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	@Override
	public void createSession() {
		// TODO Auto-generated method stub
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	@Override
	public String insert(Object obj) {
		// TODO Auto-generated method stub
		Location location = (Location) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(location);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Location location = (Location) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.update(location);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Location location = (Location) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.delete(location);

		t.commit();

		session.close();

		return "Delete Successful";
	}

	@Override
	public List<Location> selectAll() {
		// TODO Auto-generated method stub

		createSession();

		// create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// create criteria query
		CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);

		// specify criteria root
		Root<Location> rootLocation = criteriaQuery.from(Location.class);
		
		criteriaQuery.where(criteriaBuilder.equal(rootLocation.get("status"),1));
		
		// Execute query
		List<Location> locationList = session.createQuery(criteriaQuery).getResultList();

		// close session
		session.close();

		return locationList;
	}

}
