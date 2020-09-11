package com.synergisticit.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	Session session;
	
	@Override
	public User save(User user) {
		
		User uDb = findById(user.getUserId());
		
		try {
			
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (uDb == null)
				session.save(user);
			else
				session.saveOrUpdate(user);
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			session.close();
		}
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		
		List<User> listOfUsers = new ArrayList<>();
		
		try (Session session = sessionFactory.openSession();) {
			
			session.beginTransaction();
			listOfUsers = session.createQuery("from User").list();
		}
		
		return listOfUsers;
	}

	@Override
	public User findById(long id) {
		
		User uDb = null;
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			uDb = session.get(User.class, id);
			session.getTransaction().commit();
		}
		
		return uDb;
	}

	@Override
	public User update(User user) {
		
		User uDb = null;
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			uDb = session.get(User.class, user.getUserId());
			session.getTransaction().commit();
		}
		
		return uDb;
	}

	@Override
	public User updateById(long id) {
		
		User uDb = null;
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			uDb = session.get(User.class, id);
			session.getTransaction().commit();
		}
		
		return uDb;
	}

	@Override
	public void delete(User user) {
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			User uDb = session.get(User.class, user.getUserId());
			session.delete(uDb);
			session.getTransaction().commit();
		}
	}

	@Override
	public void deleteById(long id) {
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			User uDb = session.get(User.class, id);
			session.delete(uDb);
			session.getTransaction().commit();
		}
	}

	@Override
	public boolean existsById(long id) {
		
		User uDb = null;
		
		try(Session session = sessionFactory.openSession();) {
			session.beginTransaction();
			uDb = session.get(User.class, id);
			session.getTransaction().commit();
		}
		
		return uDb != null;
	}

	@Override
	public boolean existsByName(String name) {
		
		String hql = "from User where name=:name";
		try (Session session = sessionFactory.openSession();) {
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			return query.getResultList().size() == 1;
		}
	}

	@Override
	public User findByIdAndName(Long userId, String name) {
		
		User uDb = null;
		String hql = "from User where userId = :userId and name = :name";
		
		try (Session session = sessionFactory.openSession();) {
			Query query = session.createQuery(hql);
			query.setParameter("userId", userId);
			query.setParameter("name", name);
			uDb = (User) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uDb;
	}

	@Override
	public User findByName(String name) {
		
		User uDb = null;
		String hql = "from User where name=:name";
		try (Session session = sessionFactory.openSession();) {
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			uDb = (User) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uDb;
	}

}
