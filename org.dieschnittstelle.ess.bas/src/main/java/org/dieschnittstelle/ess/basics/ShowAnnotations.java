package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.dieschnittstelle.ess.basics.reflection.ReflectedStockItemBuilder.getAccessorNameForField;
import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

	public static void main(String[] args) {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * TODO BAS2
	 */
	private static void showAttributes(Object instance) {
		show("class is: " + instance.getClass());

		Class klass = instance.getClass(); // hierüber attribute und getter ermitteln, getDeclaredMethod?, invoke?, Typen nicht zu ermitteln
		// über getdeclaredfield iterieren, also die field objekte und darüber zu gettern und attributwerten

		// über attribute iterieren und lesen werte für instance aus (die attribute die auf instance gesetzt sind)
		//von java object zu text (müssen uns nicht um typen der attribute kümmern
		// welche attribute gibt es, was sind die namen, was sind die werte, von name des attributs zu getter gehen
		StringBuilder sb = new StringBuilder();

		try {

			// TODO BAS2: create a string representation of instance by iterating
			//  over the object's attributes / fields as provided by its class
			//  and reading out the attribute values. The string representation
			//  will then be built from the field names and field values.
			//  Note that only read-access to fields via getters or direct access
			//  is required here.
			Field[] fields = klass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				//field.setAccessible(true);

				String getterName = getAccessorNameForField("get", fields[i].getName()); //was ist, falls getter für boolean (is)
				Method getterMethod = klass.getDeclaredMethod(getterName);
				show(getterMethod.invoke(instance));
				sb.append(fields[i].getName()).append(": ").append(getterMethod.invoke(instance));
				if(i < fields.length - 1) {
					sb.append(", ");
				}
			}

			show(klass.getSimpleName()+" "+ sb);


			// TODO BAS3: if the new @DisplayAs annotation is present on a field,
			//  the string representation will not use the field's name, but the name
			//  specified in the the annotation. Regardless of @DisplayAs being present
			//  or not, the field's value will be included in the string representation.

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("showAnnotations(): exception occurred: " + e,e);
		}

	}

}
