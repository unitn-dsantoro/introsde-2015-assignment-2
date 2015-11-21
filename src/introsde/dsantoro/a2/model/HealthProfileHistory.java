package introsde.dsantoro.a2.model;

import introsde.dsantoro.a2.dao.HealthCoachDao;
//import introsde.rest.ehealth.model.Person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="HealthMeasureHistory")
@NamedQuery(name="HealthProfileHistory.findAll", query="SELECT h FROM HealthProfileHistory h")
@XmlRootElement
public class HealthProfileHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	@Temporal(TemporalType.DATE)
	@Column(name="timestamp")
	private Date timestamp;

	@Column(name="value")
	private String value;

	@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;

	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthProfileHistory() {
	}

	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
	    return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
	    this.measureDefinition = param;
	}

	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	public static HealthProfileHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = HealthCoachDao.instance.createEntityManager();
		HealthProfileHistory p = em.find(HealthProfileHistory.class, id);
		HealthCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthProfileHistory> getAll() {
		EntityManager em = HealthCoachDao.instance.createEntityManager();
	    List<HealthProfileHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthProfileHistory.class).getResultList();
	    HealthCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthProfileHistory saveHealthMeasureHistory(HealthProfileHistory p) {
		EntityManager em = HealthCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    HealthCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthProfileHistory updateHealthMeasureHistory(HealthProfileHistory p) {
		EntityManager em = HealthCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    HealthCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthProfileHistory p) {
		EntityManager em = HealthCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    HealthCoachDao.instance.closeConnections(em);
	}
}