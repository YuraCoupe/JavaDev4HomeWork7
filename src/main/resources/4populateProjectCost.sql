UPDATE projects
SET project_cost=subquery.project_cost
FROM (SELECT pr.project_id, SUM(d.salary) as project_cost
	FROM projects pr
	JOIN developersToProjects dtp ON dtp.project_id = pr.project_id
	JOIN developers d ON dtp.developer_id = d.developer_id
	GROUP BY pr.project_id) AS subquery
WHERE projects.project_id=subquery.project_id;