export function formatPastHour(pastHour: number | null | undefined): string {
    const hour = Number(pastHour ?? 0);

    if (!Number.isFinite(hour) || hour < 0) {
        return "刚刚";
    }

    if (hour < 1) {
        return "刚刚";
    }

    if (hour < 24) {
        return `${Math.floor(hour)}小时前`;
    }

    return `${Math.floor(hour / 24)}天前`;
}
