package world.pasds.back.privateData.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.privateData.entity.QPrivateData;
import world.pasds.back.privateData.entity.QPrivateDataRole;
import world.pasds.back.role.entity.QRole;
import world.pasds.back.role.entity.QRoleAuthority;
import world.pasds.back.role.entity.Role;

import java.util.List;

@RequiredArgsConstructor
public class PrivateDataCustomRepositoryImpl implements PrivateDataCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PrivateData> findAccessiblePrivateData(Long teamId, Role userRole, Pageable pageable) {
        QPrivateData qPrivateData = QPrivateData.privateData;
        QPrivateDataRole qPrivateDataRole = QPrivateDataRole.privateDataRole;
        QRoleAuthority qRoleAuthority = QRoleAuthority.roleAuthority;
        QRole qRole = QRole.role;

        // 역할에 따른 접근 권한을 확인하는 쿼리 부분
        List<PrivateData> results = queryFactory
                .selectDistinct(qPrivateData)
                .from(qPrivateData)
                .join(qPrivateDataRole).on(qPrivateDataRole.privateData.eq(qPrivateData))
                .join(qRole).on(qPrivateDataRole.role.eq(qRole))
                .join(qRoleAuthority).on(qRoleAuthority.role.eq(qRole))
                .where(
                        qRoleAuthority.authority.name.eq(AuthorityName.PRIVATE_DATA_READ),
                        qPrivateDataRole.role.eq(userRole),
                        qPrivateData.team.id.eq(teamId)
                )
                .orderBy(qPrivateData.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(qPrivateData.count())
                .from(qPrivateData)
                .join(qPrivateDataRole).on(qPrivateDataRole.privateData.eq(qPrivateData))
                .join(qRole).on(qPrivateDataRole.role.eq(qRole))
                .join(qRoleAuthority).on(qRoleAuthority.role.eq(qRole))
                .where(
                        qRoleAuthority.authority.name.eq(AuthorityName.PRIVATE_DATA_READ),
                        qPrivateDataRole.role.eq(userRole),
                        qPrivateData.team.id.eq(teamId)
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(results, pageable, () -> total != null ? total : 0);
    }
}
