import React from 'react'

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            hits: [],
            isLoading: false,
            error: null,
            bookName: ''
        };
    }


    handleSubmit(event) {
        this.state.bookName = event.state.value;
        event.preventDefault();
    }

    componentDidMount() {
        this.setState({ isLoading: true });
        let url = this.state.bookName === '' ? 'http://localhost:8080/book' : 'http://localhost:8080/book/name/' + this.state.bookName;

        fetch(url)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(JSON.stringify(response.status));
                }
            })
            .then(data => this.setState({ hits: data, isLoading: false }))
            .catch(error => this.setState({ error, isLoading: false }));
    }

    render() {
        const { hits, isLoading, error } = this.state;

        if (error) {
            return <p>{error.message}</p>;
        }

        if (isLoading) {
            return <p>Loading ...</p>;
        }

        return (
            <React.Fragment>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Имя книги:
                        <input type="text" value={this.state.value} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="Отправить" />
                </form>
                <ul>
                    {hits.map(hit =>
                        <li key={hit.id}> id = {hit.id}, name = {hit.name}
                            {hit.authors.map(author => <p>id = {author.id} name = {author.name}</p>)}
                            {hit.genres.map(genre => <p>id = {genre.id} name = {genre.name}</p>)}
                        </li>
                    )}
                </ul>
            </React.Fragment>
        );
    }
};
