package hospitalpets.control;


import org.hibernate.classic.Session;
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * @netbeans.hibernate.util
 */
public class Configuracion {
    public static final SessionFactory sessionFactory;

    static {
        try {
        // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
        // Make sure you log the exception, as it might be swallowed
            System.err.println("Creacion fallida de la SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final ThreadLocal session = new ThreadLocal();

    /**
     * 
     * @throws org.hibernate.HibernateException 
     * @return 
     */
    public Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
        // Store it in the ThreadLocal variable
            session.set(s);
        }
        return s;
    }
    public SessionFactory getSesion(){
        return sessionFactory;
    }
    public void cerrar(){
        sessionFactory.close();        
    }
    /**
     * 
     * @throws org.hibernate.HibernateException 
     */
    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        if (s != null)
            s.close();
        session.set(null);
    }

}