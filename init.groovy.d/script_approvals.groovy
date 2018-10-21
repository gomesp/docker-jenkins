import org.jenkinsci.plugins.scriptsecurity.scripts.*

ScriptApproval.get().clearApprovedSignatures()

[
    "method java.lang.Throwable printStackTrace"
].each {
    ScriptApproval.get().approveSignature(it)
}

println "** Script approvals done"