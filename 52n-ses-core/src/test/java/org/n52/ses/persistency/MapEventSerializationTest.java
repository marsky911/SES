/**
 * ﻿Copyright (C) 2008 - 2014 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * icense version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.ses.persistency;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.junit.Assert;
import org.junit.Test;
import org.n52.ses.api.event.MapEvent;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class MapEventSerializationTest {

	@Test
	public void testSerialization() throws XmlException, IOException, ClassNotFoundException {
		MapEvent me = new MapEvent(2, 3);
		me.put(MapEvent.ORIGNIAL_MESSAGE_KEY, XmlObject.Factory.parse("<test><content>123</content></test>"));
		me.put(MapEvent.DOUBLE_VALUE_KEY, 23.5);
		me.put(MapEvent.SENSORID_KEY, "theSensor");
		GeometryFactory gf = new GeometryFactory();
		Point geom = gf.createPoint(new Coordinate(52.1, 7.44112233));
		me.put(MapEvent.GEOMETRY_KEY, geom);
		
		Object o = MapEvent.deserialize(new ByteArrayInputStream(me.serialize()));
		
		Assert.assertTrue(o instanceof MapEvent);
		MapEvent ome = (MapEvent) o;
		Assert.assertTrue(XmlObject.Factory.parse("<test><content>123</content></test>").valueEquals((XmlObject) ome.get(MapEvent.ORIGNIAL_MESSAGE_KEY)));
		Assert.assertTrue((Double) ome.get(MapEvent.DOUBLE_VALUE_KEY) == 23.5);
		Assert.assertTrue(ome.get(MapEvent.SENSORID_KEY).equals("theSensor"));
		Assert.assertTrue((Long) ome.get(MapEvent.START_KEY) == 2);
		Assert.assertTrue((Long) ome.get(MapEvent.END_KEY) == 3);
		Assert.assertTrue(((Point) ome.get(MapEvent.GEOMETRY_KEY)).equals(geom));
	}
	
}
