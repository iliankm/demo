package com.iliankm.demo.controller;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.PRAGMA;

/**
 * Calendar resource REST controller.
 */
@RestController
@RequestMapping("api/v1/calendar")
class CalendarController {

    /**
     * Example GET endpoint for creating calendar event.
     *
     * @return the event in ics format
     */
    @GetMapping
    ResponseEntity<Resource> getCalendar() {
        //time zone
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Berlin"); //Calendar.getInstance().getTimeZone();
        //start and end date with time zone
        ZonedDateTime startZ = ZonedDateTime.of(LocalDateTime.now(), timeZone.toZoneId());

        //the event
        VEvent meeting = new VEvent();
        meeting.add(new DtStart<>(startZ));
        meeting.add(new net.fortuna.ical4j.model.property.Duration(Duration.ofMinutes(120)));
        meeting.add(new Summary("Test Appointment"));
        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();
        meeting.add(uid);
        meeting.add(new TzId(timeZone.toZoneId().getId()));
        try {
            meeting.add(new Organizer("bav-beraterBerater1@allianz.de"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        meeting.add(new Description("Appointment description goes here..."));
        meeting.add(new Categories("Category1"));
        meeting.add(new Location("GoTo-Meeting"));
        meeting.add(new XProperty("prop1", "Some value"));
        meeting.add(new XProperty("prop2", "Some other value"));

        Calendar icsCalendar = new Calendar();
        icsCalendar.add(Method.REQUEST);
        icsCalendar.add(meeting);

        final Resource resource = new ByteArrayResource(icsCalendar.toString().getBytes());
        final HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, "attachment; filename=meeting.ics");
        headers.add(CONTENT_TYPE, "text/calendar; charset=utf-8");
        headers.add(CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(PRAGMA, "no-cache");
        headers.add(EXPIRES, "0");
        headers.add("Meeting-Id", uid.getValue());

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}