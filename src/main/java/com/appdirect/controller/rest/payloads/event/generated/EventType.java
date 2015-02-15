
package com.appdirect.controller.rest.payloads.event.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creator" type="{}creatorType"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="marketplace" type="{}marketplaceType"/>
 *         &lt;element name="payload" type="{}payloadType"/>
 *         &lt;element name="returnUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventType", propOrder = {
    "creator",
    "flag",
    "marketplace",
    "payload",
    "returnUrl",
    "type"
})
public class EventType {

    @XmlElement(required = true)
    protected CreatorType creator;
    @XmlElement(required = true)
    protected String flag;
    @XmlElement(required = true)
    protected MarketplaceType marketplace;
    @XmlElement(required = true)
    protected PayloadType payload;
    @XmlElement(required = true)
    protected String returnUrl;
    @XmlElement(required = true)
    protected String type;

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link CreatorType }
     *     
     */
    public CreatorType getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreatorType }
     *     
     */
    public void setCreator(CreatorType value) {
        this.creator = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * Gets the value of the marketplace property.
     * 
     * @return
     *     possible object is
     *     {@link MarketplaceType }
     *     
     */
    public MarketplaceType getMarketplace() {
        return marketplace;
    }

    /**
     * Sets the value of the marketplace property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketplaceType }
     *     
     */
    public void setMarketplace(MarketplaceType value) {
        this.marketplace = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link PayloadType }
     *     
     */
    public PayloadType getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayloadType }
     *     
     */
    public void setPayload(PayloadType value) {
        this.payload = value;
    }

    /**
     * Gets the value of the returnUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * Sets the value of the returnUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnUrl(String value) {
        this.returnUrl = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
