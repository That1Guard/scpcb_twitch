Type Timer
    Field interval
    Field updater
    Field loop
    Field timerfunc$
    Field paramsList$
End Type

; -------------- Timers ----------------

Function CreateTimer(public$, interval, loop, databank$)
    Local t.Timer = New Timer
    t\interval = interval
    t\updater = MilliSecs() + interval
    t\loop = loop
    t\timerfunc = public$
    t\paramsList = databank$
	
    Return Handle(t)
End Function

Function UpdateTimers()
    For t.Timer = Each Timer
		If t\updater < MilliSecs() Then
            Local paramsList$ = t\paramsList
            Select FunctionNameFromTimer(t)
                Case "YourFunctionNameHere1"
                    YourFunctionName1(paramsList)
                Case "YourFunctionNameHere2"
                    YourFunctionName2(paramsList)
                Case "StartNewVoteRound"
                    StartNewVoteRound()
                Case "TimedVoteEvent"
                    TimedVoteEvent(paramsList)
                Case "EndVotePassive"
                    EndVotePassive(paramsList)
				Default
					NoFunctionFound()
			End Select
			
			If t\loop Then
				t\updater = MilliSecs() + t\interval
			Else
				RemoveTimer(t)
			End If
			
		End If
	Next
End Function

Function RemoveTimer(t.Timer)
    Delete t
End Function

Function RemoveTimerObject(timer)
	RemoveTimer(Object.Timer(timer))
End Function
	
; ------------- Test Functions ----------------

Function FunctionNameFromTimer$(t.Timer)
    Return t\timerfunc$
End Function

Function YourFunctionName1(paramsList$)
	Print "Function 1 executed with parameters: " + paramsList$
End Function

Function YourFunctionName2(paramsList$)
	Print "Function 2 executed with parameters: " + paramsList$
End Function


; /////////////////////////////////////////////////////////////////
;           Twitch Function for Timers
; /////////////////////////////////////////////////////////////////
Function StartNewVoteRound()
    SendDataToServer("!vstart")
End Function

Function TimedVoteEvent(paramsList$)

End Function

Function EndVotePassive(paramsList$)
    Local commandEnd = Instr(paramsList$, " ")
    If commandEnd > 0 Then
        Local command$ = Left(paramsList$, commandEnd - 1)
        Local parameter$ = Mid(paramsList$, commandEnd + 1)
        
        ; CreateConsoleMsg("[TWITCH][DEBUG]: command reciev: '" + command + "'")
        Select command$
            Case "command:"
                ; CreateConsoleMsg("[TWITCH][DEBUG]: command reciev: '" + parameter + "'")
                ExecuteConsoleCommand(parameter$)
            Case "particle:"
                CreateConsoleMsg("[TWITCH] Handle and pass particle data for the game with parameter: " + parameter$, 255, 0, 0)
            Default
                CreateConsoleMsg("[TWITCH] Unknown command: " + command$, 255, 0, 0)
        End Select
    Else
        CreateConsoleMsg("[TWITCH] Invalid paramsList format.", 255, 0, 0)
    End If
End Function

Function NoFunctionFound()
	Print "No Function found during execution."
End Function
