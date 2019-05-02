package de.rakuten.cloud.service.productapi.enums;

import de.rakuten.cloud.service.productapi.exceptions.InvalidProductTypeException;

public enum ProductTypeEnum {
	PHYSICAL("physical"), VIRTUAL("virtual");

	private String type;

	private ProductTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static ProductTypeEnum getCorrespondentType(String type) throws InvalidProductTypeException {
		for (ProductTypeEnum value : ProductTypeEnum.values()) {
			if (value.getType().equalsIgnoreCase(type)) {
				return value; 
			}
		}
		throw new InvalidProductTypeException("Invalid Product Type informed.");
	}
}
