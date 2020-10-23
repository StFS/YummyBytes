# YummyBytes

YummyBytes is a simple Java library to convert between various byte size 
units.

It can also handle converting string representations of byte sizes such
as "1 megabyte" or "3Gb" into representative Java objects.

The library can handle both SI and IEC units.

## Example Code

```java
        // Create a ByteSize
        ByteSize gigabytes = new ByteSize(42, ByteSizeUnit.GIGABYTES);

        System.out.println(gigabytes.toString()); // "42 GB"

        // Convert it to kilobytes
        ByteSize kilobytes = gigabytes.convertTo(ByteSizeUnit.KILOBYTES);

        System.out.println(kilobytes.toString()); // "42000000 KB"

        // You can also convert IEC to SI units and vice-versa
        ByteSize gibibytes = gigabytes.convertTo(ByteSizeUnit.GIBIBYTES);

        System.out.println(gibibytes.toString()); // "39.11554813385009765625 GiB"

        // Comparing different units is a breeze
        System.out.println(gibibytes.equals(gigabytes)); // true

        // And you can convert various string formats easily, which is awesome for parsing configuration values
        System.out.println(ByteSize.from("25 exabytes")); // "25 EB"
        System.out.println(ByteSize.from("7 kibibytes")); // "7 KiB"
        System.out.println(ByteSize.from("2MB")); // "2 MB"
        System.out.println(ByteSize.from("2M")); // "2 MiB"
```
    