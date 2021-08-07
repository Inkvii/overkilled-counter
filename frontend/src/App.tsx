import React from 'react'
import {BrowserRouter, Route, Switch} from "react-router-dom"
import CounterView from "view/CounterView"
import AllCounters from "view/AllCounters"

function App() {
	return (
		<div>
			<BrowserRouter>
				<Switch>
					<Route exact path={"/"} component={CounterView}/>
					<Route exact path={"/list"} component={AllCounters}/>
				</Switch>
			</BrowserRouter>
		</div>
	)
}

export default App
