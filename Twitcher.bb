Global EnableTwitchIntegration = GetINIInt(OptionFile, "twitch", "enable Twitch")
Global TwitchMaxEventTime = GetINIInt(OptionFile, "twitch", "max Twitch Event Time")
Global TwitchMaxEvents = GetINIInt(OptionFile, "twitch", "max Twitch Events")

Function DetermineVoteTime()
    ttl% = (TwitchMaxEvents * TwitchMaxEventTime) * 1000
    Return ttl%
End Function