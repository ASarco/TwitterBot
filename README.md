# TwitterBot

This is a quick example of how to create a Twitter bot, using the famous Twitter4j library.

There are a few classes in Commons which can be reused, particularely AbstractStatusListener, which will take care of replying and adding mentions, and logging exceptions.
CircularBuffer is used to keep a repetitive buffer of possible responses. This is needed because Twitter will complain or restrict your account if you publish the same response every time.

Take a look at the 2 implementations included, to see how you can implement your own.

You need to provide a twitter4j.properties file with the credentials for every bot, at src/main/resources to run it locally, or see other options at http://twitter4j.org/en/configuration.html

