package com.iqvia.tools.shared.model.AccountingSchema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Row from {@code FULFILLMENT_BUSINESS_LINES} (PO# and cost center for exports / reporting).
 */
@Entity
@Table(name = "FULFILLMENT_BUSINESS_LINES")
@NamedQuery(name = "FulfillmentBusinessLine.findAllOrdered", query = "SELECT f FROM FulfillmentBusinessLine f ORDER BY f.businessLineDesc")
public class FulfillmentBusinessLine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BUSINESS_LINE_ID")
	private Long fulfillmentBusinessLineId;

	@Column(name = "BUSINESS_LINE_DESC")
	private String businessLineDesc;

	@Column(name = "BUSINESS_LINE_PO_NUMBER")
	private String poNumber;

	@Column(name = "BUSINESS_LINE_COST_CENTER")
	private String costCenter;

	public Long getFulfillmentBusinessLineId() {
		return fulfillmentBusinessLineId;
	}

	public void setFulfillmentBusinessLineId(Long fulfillmentBusinessLineId) {
		this.fulfillmentBusinessLineId = fulfillmentBusinessLineId;
	}

	public String getBusinessLineDesc() {
		return businessLineDesc;
	}

	public void setBusinessLineDesc(String businessLineDesc) {
		this.businessLineDesc = businessLineDesc;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
}
