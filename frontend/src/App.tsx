import React from 'react'
import {BrowserRouter, Route, Switch} from "react-router-dom"
import CounterView from "view/CounterView"
import AllCounters from "view/AllCounters"

function App() {
	return (
		<div>
			<BrowserRouter>
				<Switch>
					<Route exact path={"/:paramName"} component={CounterView}/>
					<Route exact path={"/"} component={AllCounters}/>
				</Switch>
			</BrowserRouter>
		</div>
	)
}

export default App
