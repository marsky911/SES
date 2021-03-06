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
package org.n52.ses.common.https;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.muse.ws.addressing.soap.SoapConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for handling outgoing https connections.
 * 
 * @author Matthes Rieke <m.rieke@uni-muenster.de>
 *
 */
public class HTTPSConnectionHandler implements SoapConnectionHandler {

	private static final Logger logger = LoggerFactory.getLogger(HTTPSConnectionHandler.class);


	/* (non-Javadoc)
	 * @see org.apache.muse.ws.addressing.soap.SoapConnectionHandler#afterSend(java.net.URLConnection)
	 */
	@Override
	public void afterSend(URLConnection conn) {
		logger.debug("post sending ops..");
	}

	/* (non-Javadoc)
	 * @see org.apache.muse.ws.addressing.soap.SoapConnectionHandler#beforeSend(java.net.URLConnection)
	 */
	@Override
	public void beforeSend(URLConnection conn) {
		if (conn.getURL().getProtocol().equals("https")) {
			try {
				conn.getURL().openConnection();
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}		
	}

	/**
	 * @param args as
	 */
	public static void main(String[] args) {
		URLConnection conn;
		try {
			conn = new URL("https://project.gwdi.eu/ows-client/consumer").openConnection();
			new HTTPSConnectionHandler().beforeSend(conn);
		} catch (MalformedURLException e) {
			logger.warn(e.getMessage(), e);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}

	}


}
