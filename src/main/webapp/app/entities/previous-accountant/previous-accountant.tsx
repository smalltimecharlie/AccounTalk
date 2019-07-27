import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './previous-accountant.reducer';
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPreviousAccountantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PreviousAccountant extends React.Component<IPreviousAccountantProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { previousAccountantList, match } = this.props;
    return (
      <div>
        <h2 id="previous-accountant-heading">
          <Translate contentKey="accounTalkApp.previousAccountant.home.title">Previous Accountants</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.previousAccountant.home.createLabel">Create new Previous Accountant</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {previousAccountantList && previousAccountantList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.previousAccountant.name">Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.previousAccountant.email">Email</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.previousAccountant.phoneNumber">Phone Number</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.previousAccountant.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {previousAccountantList.map((previousAccountant, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${previousAccountant.id}`} color="link" size="sm">
                        {previousAccountant.id}
                      </Button>
                    </td>
                    <td>{previousAccountant.name}</td>
                    <td>{previousAccountant.email}</td>
                    <td>{previousAccountant.phoneNumber}</td>
                    <td>
                      {previousAccountant.client ? (
                        <Link to={`client/${previousAccountant.client.id}`}>{previousAccountant.client.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${previousAccountant.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${previousAccountant.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${previousAccountant.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="accounTalkApp.previousAccountant.home.notFound">No Previous Accountants found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ previousAccountant }: IRootState) => ({
  previousAccountantList: previousAccountant.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PreviousAccountant);
