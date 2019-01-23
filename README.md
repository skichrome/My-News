# My News

***

[![Build Status](https://travis-ci.org/skichrome/My-News.svg?branch=master)](https://travis-ci.org/skichrome/My-News)

***

This is an Android Project for a formation on [OpenClassrooms](https://openclassrooms.com/projects/renouez-avec-l-actualite)

### Credentials
Before launch my app you need a valid New York Time API Key (this app need Top Stories API, Most Popular API and Article Search API).
You have to create a new Java enum named "Credentials.java" with this template in  "com.skichrome.mynews.util" package :

```java
package com.skichrome.mynews.util;

public enum Credentials
{
    NYT_API_KEY("NYT_KEY", "YOUR_API_KEY_HERE");

    private String name;
    private String key;

    Credentials(String nyt_key, String s)
    {
        this.key = s;
        this.name = nyt_key;
    }

    public String getName() { return name; }
    public String getKey() { return key; }
}
```