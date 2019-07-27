import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './per-pension-contributions.reducer';
import { IPerPensionContributions } from 'app/shared/model/per-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPerPensionContributionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PerPensionContributions extends React.Component<IPerPensionContributionsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { perPensionContributionsList, match } = this.props;
    return (
      <div>
        <h2 id="per-pension-contributions-heading">
          <Translate contentKey="accounTalkApp.perPensionContributions.home.title">Per Pension Contributions</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.perPensionContributions.home.createLabel">Create new Per Pension Contributions</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {perPensionContributionsList && perPensionContributionsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.perPensionContributions.netAmountPaid">Net Amount Paid</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.perPensionContributions.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.perPensionContributions.pensionProvider">Pension Provider</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {perPensionContributionsList.map((perPensionContributions, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${perPensionContributions.id}`} color="link" size="sm">
                        {perPensionContributions.id}
                      </Button>
                    </td>
                    <td>{perPensionContributions.netAmountPaid}</td>
                    <td>
                      {perPensionContributions.taxReturn ? (
                        <Link to={`tax-return/${perPensionContributions.taxReturn.id}`}>{perPensionContributions.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {perPensionContributions.pensionProvider ? (
                        <Link to={`pension-provider/${perPensionContributions.pensionProvider.id}`}>
                          {perPensionContributions.pensionProvider.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${perPensionContributions.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${perPensionContributions.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${perPensionContributions.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.perPensionContributions.home.notFound">No Per Pension Contributions found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ perPensionContributions }: IRootState) => ({
  perPensionContributionsList: perPensionContributions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PerPensionContributions);
