import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './shares.reducer';
import { IShares } from 'app/shared/model/shares.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISharesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Shares extends React.Component<ISharesProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { sharesList, match } = this.props;
    return (
      <div>
        <h2 id="shares-heading">
          <Translate contentKey="accounTalkApp.shares.home.title">Shares</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.shares.home.createLabel">Create new Shares</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {sharesList && sharesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.shares.companyName">Company Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.shares.numberOfShares">Number Of Shares</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.shares.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {sharesList.map((shares, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${shares.id}`} color="link" size="sm">
                        {shares.id}
                      </Button>
                    </td>
                    <td>{shares.companyName}</td>
                    <td>{shares.numberOfShares}</td>
                    <td>{shares.client ? <Link to={`client/${shares.client.id}`}>{shares.client.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${shares.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${shares.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${shares.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.shares.home.notFound">No Shares found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ shares }: IRootState) => ({
  sharesList: shares.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Shares);
