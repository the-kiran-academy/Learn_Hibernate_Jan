package com.jbk.hibernate.dao;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.jbk.hibernate.config.HibernateConfig;
import com.jbk.hibernate.entity.Product;

public class ProductDao {

	SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

	public boolean saveProduct(Product product) {
		boolean isAdded = false;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.save(product);
			session.beginTransaction().commit();
			isAdded = true;
		}

		catch (PersistenceException e) {
			System.out.println("111");
			isAdded = false;
		}

		catch (Exception e) {

			System.out.println("333");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isAdded;

	}

	public Product getProduct(String productId) {
		Session session = null;
		Product product = null;
		try {
			session = sessionFactory.openSession();
			product = session.get(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return product;
	}

	public boolean deleteProduct(String productId) {
		Session session = null;
		boolean isDeleted = false;
		try {
			session = sessionFactory.openSession();

			Product product = getProduct(productId);

			if (product != null) {
				session.delete(product);
				session.beginTransaction().commit();
				isDeleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDeleted;
	}

	public int updateProduct(Product product) {
		Session session = null;
		int isUpdated = 0;
		try {
			session = sessionFactory.openSession();
			session.update(product);
			session.beginTransaction().commit();
			isUpdated = 1;
		}

		catch (OptimisticLockException e) {
			isUpdated = 3;
		} catch (PersistenceException e) {
			isUpdated = 2;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isUpdated;
	}

}
