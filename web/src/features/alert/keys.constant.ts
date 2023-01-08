export const Keys = {
    all: ['alerts'],
    list: (filter  :any) => [...Keys.all, {filter}] as const,
}
