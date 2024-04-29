package world.pasds.back.role.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import world.pasds.back.role.entity.QRoleAuthority;
import world.pasds.back.role.entity.Role;

@RequiredArgsConstructor
public class RoleAuthorityCustomRepositoryImpl implements RoleAuthorityCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean checkAuthority(Role role, Long authorityId) {
        QRoleAuthority qRoleAuthority = QRoleAuthority.roleAuthority;
        BooleanExpression queryCondition = qRoleAuthority.role.eq(role)
                .and(qRoleAuthority.authority.id.eq(authorityId));

        return jpaQueryFactory.select(Expressions.constant(1))
                .from(qRoleAuthority)
                .where(queryCondition)
                .fetchFirst() != null;
    }
}
