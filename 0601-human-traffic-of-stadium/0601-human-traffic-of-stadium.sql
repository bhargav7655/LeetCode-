SELECT id, visit_date, people
FROM (
    SELECT
        id,
        visit_date,
        people,
        id - ROW_NUMBER() OVER (ORDER BY id) AS grp
    FROM Stadium
    WHERE people >= 100
) t
WHERE grp IN (
    SELECT grp
    FROM (
        SELECT
            id - ROW_NUMBER() OVER (ORDER BY id) AS grp
        FROM Stadium
        WHERE people >= 100
    ) g
    GROUP BY grp
    HAVING COUNT(*) >= 3
)
ORDER BY visit_date;