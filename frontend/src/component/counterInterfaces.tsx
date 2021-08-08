export interface CounterDto {
	name: string,
	count: number
}

export interface AllCountersResponse {
	counters: CounterDto[]
}

