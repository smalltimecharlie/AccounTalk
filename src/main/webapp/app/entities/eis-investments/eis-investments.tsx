import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './eis-investments.reducer';
import { IEisInvestments } from 'app/shared/model/eis-investments.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEisInvestmentsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EisInvestments extends React.Component<IEisInvestmentsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { eisInvestmentsList, match } = this.props;
    return (
      <div>
        <h2 id="eis-investments-heading">
          <Translate contentKey="accounTalkApp.eisInvestments.home.title">Eis Investments</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.eisInvestments.home.createLabel">Create new Eis Investments</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {eisInvestmentsList && eisInvestmentsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.eisInvestments.investmentScheme">Investment Scheme</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.eisInvestments.dateInvested">Date Invested</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.eisInvestments.amountPaid">Amount Paid</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.eisInvestments.taxReturn">Tax Return</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {eisInvestmentsList.map((eisInvestments, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${eisInvestments.id}`} color="link" size="sm">
                        {eisInvestments.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`accounTalkApp.InvestmentScheme.${eisInvestments.investmentScheme}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={eisInvestments.dateInvested} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{eisInvestments.amountPaid}</td>
                    <td>
                      {eisInvestments.taxReturn ? (
                        <Link to={`tax-return/${eisInvestments.taxReturn.id}`}>{eisInvestments.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${eisInvestments.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${eisInvestments.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${eisInvestments.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.eisInvestments.home.notFound">No Eis Investments found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ eisInvestments }: IRootState) => ({
  eisInvestmentsList: eisInvestments.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EisInvestments);
