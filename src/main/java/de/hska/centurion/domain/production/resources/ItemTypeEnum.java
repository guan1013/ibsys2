package de.hska.centurion.domain.production.resources;

public enum ItemTypeEnum {
	E("E"), K("K"), P("P");

	private final String itemType;

	private ItemTypeEnum(final String itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return itemType;
	}
}
