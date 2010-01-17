/*
 * Copyright (C) 2010 Felix Bechstein
 * 
 * This file is part of WebSMS.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */
package de.ub0r.android.websms.connector.gmx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import de.ub0r.android.websms.connector.common.CommandReceiver;
import de.ub0r.android.websms.connector.common.ConnectorSpec;
import de.ub0r.android.websms.connector.common.WebSMSException;
import de.ub0r.android.websms.connector.common.ConnectorSpec.SubConnectorSpec;

/**
 * Receives commands coming as broadcast from WebSMS.
 * 
 * @author flx
 */
public class CommandReceiverGMX extends CommandReceiver {
	/** Tag for debug output. */
	private static final String TAG = "WebSMS.GMX";

	/** Preferences intent action. */
	private static final String PREFS_INTENT_ACTION = "de.ub0r.android."
			+ "websms.connectors.gmx.PREFS";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ConnectorSpec initSpec(final Context context) {
		final String name = context.getString(R.string.connector_gmx_name);
		ConnectorSpec c = new ConnectorSpec(TAG, name);
		c.setAuthor(// .
				context.getString(R.string.connector_gmx_author));
		c.setBalance(null);
		c.setPrefsIntent(PREFS_INTENT_ACTION);
		c.setPrefsTitle(context.getString(R.string.connector_gmx_preferences));
		c.setCapabilities(ConnectorSpec.CAPABILITIES_BOOSTRAP
				| ConnectorSpec.CAPABILITIES_UPDATE
				| ConnectorSpec.CAPABILITIES_SEND);
		c.addSubConnector(TAG, c.getName(),
				SubConnectorSpec.FEATURE_MULTIRECIPIENTS
						| SubConnectorSpec.FEATURE_CUSTOMSENDER
						| SubConnectorSpec.FEATURE_SENDLATER);
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ConnectorSpec updateSpec(final Context context,
			final ConnectorSpec connectorSpec) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		if (p.getBoolean(Preferences.PREFS_ENABLED, false)) {
			if (p.getString(Preferences.PREFS_MAIL, "").length() > 0
					&& p.getString(Preferences.PREFS_PASSWORD, "") // .
							.length() > 0) {
				connectorSpec.setReady();
			} else {
				connectorSpec.setStatus(ConnectorSpec.STATUS_ENABLED);
			}
		} else {
			connectorSpec.setStatus(ConnectorSpec.STATUS_INACTIVE);
		}
		return connectorSpec;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doBootstrap(final Intent intent)
			throws WebSMSException {
		// do nothing by default
		Log.d(TAG, "bootstrap");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doUpdate(final Intent intent) throws WebSMSException {
		// do nothing by default
		Log.d(TAG, "update");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void doSend(final Intent intent) throws WebSMSException {
		// do nothing by default
		Log.d(TAG, "send");
	}
}
