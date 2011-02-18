package com.sunlightlabs.android.congress.notifications.subscribers;

import java.util.List;

import android.content.Intent;
import android.util.Log;

import com.sunlightlabs.android.congress.FloorUpdateList;
import com.sunlightlabs.android.congress.notifications.Subscriber;
import com.sunlightlabs.android.congress.notifications.Subscription;
import com.sunlightlabs.android.congress.utils.Utils;
import com.sunlightlabs.congress.models.CongressException;
import com.sunlightlabs.congress.models.FloorUpdate;
import com.sunlightlabs.congress.services.FloorUpdateService;

public class FloorUpdatesSubscriber extends Subscriber {

	@Override
	public String decodeId(Object result) {
		return String.valueOf(((FloorUpdate) result).timestamp.getTime());
	}

	@Override
	public List<?> fetchUpdates(Subscription subscription) {
		Utils.setupRTC(context);
		String chamber = subscription.data;
		
		try {
			return FloorUpdateService.latest(chamber, 1, FloorUpdateList.PER_PAGE);
		} catch (CongressException e) {
			Log.w(Utils.TAG, "Could not fetch the latest floor updates for " + subscription, e);
			return null;
		}
	}

	@Override
	public String notificationMessage(Subscription subscription, int results) {
		String chamber = Utils.capitalize(subscription.data);
		if (results == FloorUpdateList.PER_PAGE)
			return results + " or more new updates from the " + chamber + " floor.";
		else if (results > 1)
			return results + " new updates from the " + chamber + " floor.";
		else
			return results + " new update from the " + chamber + " floor.";
	}
	
	@Override
	public Intent notificationIntent(Subscription subscription) {
		return new Intent()
			.setClassName("com.sunlightlabs.android.congress", "com.sunlightlabs.android.congress.FloorUpdateList")
			.putExtra("chamber", subscription.data);
	}
}