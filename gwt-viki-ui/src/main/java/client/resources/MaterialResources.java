package client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ImageResource;

public interface MaterialResources extends ClientBundleWithLookup {

	MaterialResources INSTANCE = GWT.create(MaterialResources.class);

	@Source("img/profile.jpg")
	ImageResource profile();

	@Source("img/empty_tiles.png")
	ImageResource emptyTiles();

	@Source("img/viki_logo.png")
	ImageResource logo();

	@Source("img/cashier.png")
	ImageResource emptyPurchase();

}