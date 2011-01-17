package com.github.simplenotes.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.test.AndroidTestCase;

import com.github.simplenotes.Note;
import com.github.simplenotes.NotesDb;

public class NotesDbTest extends AndroidTestCase {

    private NotesDb db;

    public void setUp() {
        db = new NotesDb(getContext());
        db.open();
    }

    public void tearDown() {
        db.close();
    }

    public void testCanCreateNoteWithoutTagsAndReadIt() {
        String content = "foo bar baz";
        long id = db.createNote(content, null);
        assertTrue("Note creation failed.", id != -1);
        Note note = db.getNote(id);
        assertEquals(id, note.getId());
        assertEquals(content, note.getContent());
        assertEquals(0, note.getTags().size());
    }

    public void testCanCreateNoteWithTagsAndReadIt() {
        String content = "foo bar baz";
        List<String> tags = Arrays.asList(new String[] {"foo", "bar"});
        long id = db.createNote(content, tags);
        assertTrue("Note creation failed.", id != -1);
        Note note = db.getNote(id);
        assertEquals(id, note.getId());
        assertEquals(content, note.getContent());
        List<String> noteTags = note.getTags();
        assertEquals(tags.size(), noteTags.size());
        assertEquals(tags, noteTags);
    }

    public void testCanCreateFromNoteObjectAndReadIt() {
        Note note0 = new Note();
        note0.setContent("Creating note from note object.");
        note0.setKey("key");
        note0.setTags(Arrays.asList(new String[] {"foo", "bar", "baz"}));
        note0.setSystemTags(Arrays.asList(new String[] {"pinned", "unread"}));
        note0.setCreateDate(new Date(1295216117000L));
        note0.setModifyDate(new Date(1295216118000L));
        note0.setDeleted(false);
        note0.setSyncNum(10);
        note0.setVersion(15);
        note0.setMinVersion(20);
        note0.setShareKey("sharekey");
        note0.setPublishKey("publishkey");
        long id = db.createNote(note0);
        assertTrue("Note creation failed.", id != -1);
        Note note1 = db.getNote(id);
        assertEquals(id, note1.getId());
        assertEquals(note0.getContent(), note1.getContent());
        assertEquals(note0.getKey(), note1.getKey());
        assertEquals(note0.getTags(), note1.getTags());
        assertEquals(note0.getCreateDate(), note1.getCreateDate());
        assertEquals(note0.getModifyDate(), note1.getModifyDate());
        assertEquals(note0.isDeleted(), note1.isDeleted());
        assertEquals(note0.getSyncNum(), note1.getSyncNum());
        assertEquals(note0.getVersion(), note1.getVersion());
        assertEquals(note0.getMinVersion(), note1.getMinVersion());
        assertEquals(note0.getShareKey(), note1.getShareKey());
        assertEquals(note0.getPublishKey(), note1.getPublishKey());

        // Check the system tags via isPinned() and isUnread() since
        // the order of the corresponding tags is undefined.
        assertEquals(note0.isPinned(), note1.isPinned());
        assertEquals(note0.isUnread(), note1.isUnread());
    }

}
