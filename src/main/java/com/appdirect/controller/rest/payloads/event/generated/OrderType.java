
package com.appdirect.controller.rest.payloads.event.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="editionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="item" type="{}itemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pricingDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderType", propOrder = {
    "editionCode",
    "item",
    "pricingDuration"
})
public class OrderType {

    @XmlElement(required = true)
    protected String editionCode;
    protected List<ItemType> item;
    @XmlElement(required = true)
    protected String pricingDuration;

    /**
     * Gets the value of the editionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditionCode() {
        return editionCode;
    }

    /**
     * Sets the value of the editionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditionCode(String value) {
        this.editionCode = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemType }
     * 
     * 
     */
    public List<ItemType> getItem() {
        if (item == null) {
            item = new ArrayList<ItemType>();
        }
        return this.item;
    }

    /**
     * Gets the value of the pricingDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingDuration() {
        return pricingDuration;
    }

    /**
     * Sets the value of the pricingDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingDuration(String value) {
        this.pricingDuration = value;
    }

}
