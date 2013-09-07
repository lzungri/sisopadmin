package ar.com.grupo306.sisopadmin.persistence.impl.hibernate.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Responsabilidad:<br>
 * <li>Instancia y mantiene referencia al SessionFactory.
 * <li>Instancia las Session.
 * <li>Administra la transacción.
 *
 * @author Leandro
 */
public class SessionManager {
	private static SessionFactory sessionFactory;
	
	/**
	 * Cantidad de transacciones abiertas por un thread.
	 */
	private static ThreadLocal<Integer> transactionCount;
	
	/**
	 * Booleano que indica si una transacción dentro de las posibles anidades
	 * ejecutó rollback,
	 */
	private static ThreadLocal<Boolean> transactionRollbacked;
	
	static {
		transactionCount = new ThreadLocal<Integer>();
		transactionRollbacked = new ThreadLocal<Boolean>();
	}
	
	
	/**
	 * Retorna el factory sobre el cual se solicitará la creación de sesiones.
	 */
	private static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = createSessionFactory();
		}
		return sessionFactory;
	}
	
	/**
	 * Abre una session con la base de datos.
	 */
	public static Session openSession() {
		return getSessionFactory().openSession();
	}
	
	/**
	 * Cierra la session actual establecida con la base de datos.
	 */
	public static void closeSession() {
		getCurrentSession().close();
	}
	
	/**
	 * Abre una transacción sobre la base (en caso de no existir una previa).
	 */
	public static Transaction beginTransaction() {
		Transaction transaction = getCurrentSession().beginTransaction();
		setTransactionCount(new Integer(getTransactionCount().intValue() + 1));
		
		return transaction;
	}
	
	/**
	 * Comitea la transacción.
	 * En caso de existir transacciones anidades posdata el comiteo a la primera de la
	 * secuencia, la cual lo ejecutará si no se realizó ningún rollback dentro de las anidadas.
	 */
	public static void commitTransaction() {
		if(getTransactionCount().intValue() > 1) {
			setTransactionCount(new Integer(getTransactionCount().intValue() - 1));
		}
		else {
			setTransactionCount(new Integer(0));
			if(!getTransactionRollbacked().booleanValue()) {
				getCurrentSession().getTransaction().commit();
			}
			else {
				getCurrentSession().getTransaction().rollback();
			}
		}
	}
	
	/**
	 * Rollbackea la transacción.
	 * En caso de existir transacciones anidades posdata el comiteo a la primera de la
	 * secuencia, la cual lo ejecutará si no se realizó ningún rollback dentro de las anidadas.
	 */
	public static void rollbackTransaction() {
		if(getTransactionCount().intValue() > 1) {
			setTransactionCount(new Integer(getTransactionCount().intValue() - 1));
			setTransactionRollbacked(Boolean.TRUE);
		}
		else {
			setTransactionCount(new Integer(0));
			Transaction transaction = getCurrentSession().getTransaction();
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Indica si la sesión actual actualmente contiene transacciones activas.
	 */
	public static boolean hasOpenTransactions() {
		return transactionCount.get() > 0;
	}
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	private static SessionFactory createSessionFactory() {
		// TODO CREAR EL .PROPERTIES!!!.
		Configuration configuration =  new Configuration().configure("/ar/com/grupo306/sisopadmin/persistence/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}
	
	private static Integer getTransactionCount() {
		if(transactionCount.get() == null) {
			setTransactionCount(new Integer(0));
		}
		return transactionCount.get();
	}
	
	private static void setTransactionCount(Integer count) {
		transactionCount.set(count);
	}
	
	private static Boolean getTransactionRollbacked() {
		if(transactionRollbacked.get() == null) {
			setTransactionRollbacked(Boolean.FALSE);	
		}
		return transactionRollbacked.get();
	}
	
	private static void setTransactionRollbacked(Boolean rollbacked) {
		transactionRollbacked.set(rollbacked);
	}	

}