import java.time.*
import java.time.format.DateTimeFormatter

private val uiDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM d, yyyy • h:mm a")

fun formatPublishedAt(iso: String, zone: ZoneId = ZoneId.systemDefault()): String {
    // Try common ISO variants: "...Z" or with offset like "+00:00"
    val instant = runCatching { Instant.parse(iso) }
        .getOrElse { runCatching { OffsetDateTime.parse(iso) }.getOrNull()?.toInstant() }
        ?: return iso // fallback to original if parsing fails

    return uiDateFormatter.withZone(zone).format(instant)
}
