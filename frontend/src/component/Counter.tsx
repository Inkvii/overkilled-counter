import {Button, Card, CardContent, Typography} from "@material-ui/core"

interface Props {
	name: string,
	count: number,
	onClickButtonFunction: Function
}

function Counter(props: Props) {
	return (
		<Card variant={"outlined"}>
			<CardContent>
				<Typography variant={"h4"}>{props.name}</Typography>
				<p>Clicking this button will try to update its value in backend</p>
				<Button variant={"outlined"} fullWidth onClick={() => props.onClickButtonFunction()}>Increment counter to {props.count + 1}</Button>
			</CardContent>
		</Card>
	)
}

export default Counter
