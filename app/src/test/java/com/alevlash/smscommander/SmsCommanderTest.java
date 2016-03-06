package com.alevlash.smscommander;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SmsCommanderTest {

    private SmsCommander _smsCommander;

    private SmsManager _mockSmsManager;
    private Context _mockContext;
    private ContentResolver _mockContentResolver;
    private Cursor _mockCursor;

    @Before
    public void setup() {
        _mockSmsManager = Mockito.mock(SmsManager.class);
        _mockContext = Mockito.mock(Context.class);
        _mockContentResolver = Mockito.mock(ContentResolver.class);
        _mockCursor = Mockito.mock(Cursor.class);

        Mockito.when(_mockContext.getContentResolver()).thenReturn(_mockContentResolver);

        _smsCommander = new SmsCommander(_mockSmsManager);
    }

    @Test
    public void getContactDisplayNameByNumber_contactExists_returnsName() {
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        Mockito.when(_mockContentResolver.query(Mockito.any(Uri.class), Mockito.eq(projection), Mockito.eq((String) null), Mockito.eq((String[]) null), Mockito.eq((String) null))).thenReturn(_mockCursor);
        Mockito.when(_mockCursor.moveToNext()).thenReturn(true);
        Mockito.when(_mockCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)).thenReturn(99);
        Mockito.when(_mockCursor.getString(99)).thenReturn("Test");

        String name = _smsCommander.getContactDisplayNameByNumber("123", _mockContext);
        Assert.assertEquals("Test", name);

        Mockito.verify(_mockCursor).close();
    }

    @Test
    public void getContactDisplayNameByNumber_contactDoesNotExist_returnsNull() {
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        Mockito.when(_mockContentResolver.query(Mockito.any(Uri.class), Mockito.eq(projection), Mockito.eq((String) null), Mockito.eq((String[]) null), Mockito.eq((String) null))).thenReturn(_mockCursor);
        Mockito.when(_mockCursor.moveToNext()).thenReturn(false);

        String name = _smsCommander.getContactDisplayNameByNumber("123", _mockContext);
        Assert.assertNull(name);

        Mockito.verify(_mockCursor).close();
    }

}