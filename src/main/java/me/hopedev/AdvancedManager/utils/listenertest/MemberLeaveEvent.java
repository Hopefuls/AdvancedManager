package me.hopedev.AdvancedManager.utils.listenertest;

import org.javacord.api.entity.auditlog.AuditLogActionType;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

public class MemberLeaveEvent implements ServerMemberLeaveListener {
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {


        event.getServer().getAuditLog(1, AuditLogActionType.MEMBER_KICK).thenAccept(auditLog -> {
            if (auditLog.getEntries().get(0).getUser().join().equals(event.getUser())) {
                final String lastLogID;

                lastLogID = auditLog.getEntries().get(0).getIdAsString();
                // Alles was hier ist wird gemacht wenn der user
                // Der gleiche ist wie vorher, müsstest aber bloß
                // den vorherigen user in nem Log machen

                // Die ID des derzeitigen Logs speichern, damit falsche Erkennungen
                // nicht passieren



            }


        });

    }
}
