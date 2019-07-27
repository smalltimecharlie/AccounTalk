import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pension-provider.reducer';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPensionProviderProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PensionProvider extends React.Component<IPensionProviderProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { pensionProviderList, match } = this.props;
    return (
      <div>
        <h2 id="pension-provider-heading">
          <Translate contentKey="accounTalkApp.pensionProvider.home.title">Pension Providers</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.pensionProvider.home.createLabel">Create new Pension Provider</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {pensionProviderList && pensionProviderList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionProvider.nameOfProvider">Name Of Provider</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionProvider.membershipNumber">Membership Number</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionProvider.payeReference">Paye Reference</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionProvider.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {pensionProviderList.map((pensionProvider, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${pensionProvider.id}`} color="link" size="sm">
                        {pensionProvider.id}
                      </Button>
                    </td>
                    <td>{pensionProvider.nameOfProvider}</td>
                    <td>{pensionProvider.membershipNumber}</td>
                    <td>{pensionProvider.payeReference}</td>
                    <td>
                      {pensionProvider.client ? <Link to={`client/${pensionProvider.client.id}`}>{pensionProvider.client.id}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${pensionProvider.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${pensionProvider.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${pensionProvider.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.pensionProvider.home.notFound">No Pension Providers found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ pensionProvider }: IRootState) => ({
  pensionProviderList: pensionProvider.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PensionProvider);
