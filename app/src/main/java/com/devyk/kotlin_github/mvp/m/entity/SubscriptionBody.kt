
sealed class SubscriptionBody(val ignored: Boolean, val subscribed: Boolean)

object IGNORED: SubscriptionBody(true, false)

object  WATCH: SubscriptionBody(false, true)

