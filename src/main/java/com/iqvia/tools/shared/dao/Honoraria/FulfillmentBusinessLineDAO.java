package com.iqvia.tools.shared.dao.Honoraria;

import java.util.List;

import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import com.iqvia.tools.shared.dao.AbstractJpaDAOAction;
import com.iqvia.tools.shared.model.AccountingSchema.FulfillmentBusinessLine;

@Repository
public class FulfillmentBusinessLineDAO extends AbstractJpaDAOAction<FulfillmentBusinessLine> {

	private static final Logger logger = Logger.getLogger(FulfillmentBusinessLineDAO.class);

	public FulfillmentBusinessLineDAO() {
		setClazz(FulfillmentBusinessLine.class);
	}

	@SuppressWarnings("unchecked")
	public List<FulfillmentBusinessLine> findAllOrdered() {
		return entityManager.createNamedQuery("FulfillmentBusinessLine.findAllOrdered").getResultList();
	}

	public boolean updatePoAndCostCenter(long id, String poNumber, String costCenter, String updatedUser) {
		try {
			Query q = entityManager.createQuery(
					"UPDATE FulfillmentBusinessLine f SET f.poNumber = :poNumber, f.costCenter = :costCenter WHERE f.fulfillmentBusinessLineId = :id");
			q.setParameter("poNumber", poNumber);
			q.setParameter("costCenter", costCenter);
			q.setParameter("id", id);
			int updated = q.executeUpdate();
			if (updated == 0) {
				logger.warn("updatePoAndCostCenter: no row updated for id=" + id);
				return false;
			}
			logger.info("updatePoAndCostCenter: id=" + id + ", user=" + updatedUser);
			return true;
		} catch (Exception e) {
			logger.error("updatePoAndCostCenter: " + e.getMessage(), e);
			return false;
		}
	}
}
