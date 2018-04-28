The contents of this folder documents the study to use more than one account on Android.

With the help of _Core Commands_, one can add additional accounts, resulting in additional database files, eg.:

```
open /data/data/com.b44t.messenger.beta/files/office.db
```

The changes in `ApplicationLoader.java` will use a different database file on every startup.
The maximun recognized amount of databases (for switching) is four.

This is the folder structure with two accounts configured:

```
localhost com.b44t.messenger.beta # ll
drwxr-x--x    5 u0_a175      u0_a175           4096 Apr 28 16:25 .
drwxrwx--x  124 system       system            8192 Apr 15 22:41 ..
drwxrwx--x    2 u0_a175      u0_a175           4096 Apr 24 17:40 cache
drwxrwxrwx    4 u0_a175      u0_a175           4096 Apr 28 16:26 files
-rw-------    1 u0_a175      u0_a175              1 Apr 28 16:26 lastused
lrwxrwxrwx    1 install      install             43 Apr 28 16:23 lib -> /data/app/com.b44t.messenger.beta-2/lib/arm
drwxrwx--x    2 u0_a175      u0_a175           4096 Apr 27 23:57 shared_prefs
localhost com.b44t.messenger.beta # ll files/
drwxrwxrwx    4 u0_a175      u0_a175           4096 Apr 28 16:26 .
drwxr-x--x    5 u0_a175      u0_a175           4096 Apr 28 16:25 ..
-rw-------    1 u0_a175      u0_a175         565248 Apr 28 16:25 messenger.db
drwx------    2 u0_a175      u0_a175           4096 Apr 24 22:58 messenger.db-blobs
-rw-------    1 u0_a175      u0_a175        3330048 Apr 28 16:26 office.db
drwx------    2 u0_a175      u0_a175           4096 Apr 27 15:37 office.db-blobs
localhost com.b44t.messenger.beta #
```

The file `lastused` contains the information about the database that was opened last.
The order of databases follows the alphabetical order when using the `ls` command.

The study should be working since v0.11.x minimum.
